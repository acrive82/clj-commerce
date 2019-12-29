(ns clj-commerce.core
  (:require
    [clj-commerce.admin :as adm]
    [clj-commerce.site :as site]
    [day8.re-frame.http-fx]
    [reagent.core :as r]
    [re-frame.core :as rf]
    [goog.events :as events]
    [goog.history.EventType :as HistoryEventType]
    [markdown.core :refer [md->html]]
    [clj-commerce.ajax :as ajax]
    [clj-commerce.events]
    [reitit.core :as reitit]
    [clojure.string :as string])
  (:import goog.History))



;(defn navbar []
;  (r/with-let [expanded? (r/atom false)]
;    [:nav.navbar.is-info>div.container
;     [:div.navbar-brand
;      [:a.navbar-item {:href "/" :style {:font-weight :bold}} "clj-commerce"]
;      [:span.navbar-burger.burger
;       {:data-target :nav-menu
;        :on-click #(swap! expanded? not)
;        :class (when @expanded? :is-active)}
;       [:span][:span][:span]]]
;     [:div#nav-menu.navbar-menu
;      {:class (when @expanded? :is-active)}
;      [:div.navbar-start
;       [nav-link "#/" "Home" :home]
;       [nav-link "#/admin" "Admin" :admin]
;       [nav-link "#/about" "About" :about]]]]))
;
;(defn navlinks
;  "Navlinks to assing to the navbar"
;  '[
;    [nav-link "#/" "Home" :home]])

(defn about-page []
  [:section.section>div.container>div.content
   [:img {:src "/img/warning_clojure.png"}]])


(def pages
  {:home #'site/home-page
   :admin #'adm/admin-page
   :about #'about-page})

(defn page []
  [:div
   [adm/navbar-admin "Admin Clojure Commerce"]
   [(pages @(rf/subscribe [:page]))]])

;; -------------------------
;; Routes

(def router
  (reitit/router
    [["/" :home]
     ["/about" :about]
     ["/admin" :admin]
     ]))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (let [uri (or (not-empty (string/replace (.-token event) #"^.*#" "")) "/")]
          (rf/dispatch
            [:navigate (reitit/match-by-path router uri)]))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:navigate (reitit/match-by-name router :home)])
  
  (ajax/load-interceptors!)
  (rf/dispatch [:fetch-docs])
  (hook-browser-navigation!)
  (mount-components))
