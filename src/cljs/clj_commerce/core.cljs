(ns clj-commerce.core
  (:require
   [clj-commerce.admin :as admin]
   [clj-commerce.site :as site]
   [reagent.core :as r]
    [goog.events :as events]
    [goog.history.EventType :as HistoryEventType]
    [markdown.core :refer [md->html]]
    [clj-commerce.ajax :as ajax]
    [ajax.core :refer [GET POST]]
    [reitit.core :as reitit]
    [clojure.string :as string])
  (:import goog.History))

(defonce session (r/atom {:page :home}))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href   uri
    :class (when (= page (:page @session)) "is-active")}
   title])

;(defn navbar []
;  (r/with-let [expanded? (r/atom false)]
;    [:nav.navbar.is-info>div.container
;     [:div.navbar-brand
;       [:a.navbar-item {:href "/" :style {:font-weight :bold}} "deleteme"]
;      [:span.navbar-burger.burger
;       {:data-target :nav-menu
;        :on-click #(swap! expanded? not)
;        :class (when @expanded? :is-active)}
;       [:span][:span][:span]]]
;     [:div#nav-menu.navbar-menu
;      {:class (when @expanded? :is-active)}
;      [:div.navbar-start
;       [nav-link "#/" "Home" :home]
;       [nav-link "#/about" "About" :about]]]]))

(defn about-page []
  [:section.section>div.container>div.content
   [:img {:src "/img/warning_clojure.png"}]])

(defn home-page []
  [:section.section>div.container>div.content
   (when-let [docs (:docs @session)]
     [:div {:dangerouslySetInnerHTML {:__html (md->html docs)}}])])

(def pages
  {:home #'home-page
   :admin #'admin/admin-page
   :about #'about-page})

(defn page []
  [(pages (:page @session))])

;; -------------------------
;; Routes

(def router
  (reitit/router
   [["/" :home]
    ["/admin" :admin]
     ["/about" :about]]))

(defn match-route [uri]
  (->> (or (not-empty (string/replace uri #"^.*#" "")) "/")
       (reitit/match-by-path router)
       :data
       :name))
;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (swap! session assoc :page (match-route (.-token event)))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(swap! session assoc :docs %)}))

(defn mount-components []
  (r/render [#'admin/navbar-admin "Clojure Commerce"] (.getElementById js/document "navbar"))
  (r/render [#'admin/aside-menu] (.getElementById js/document "asidemenu"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (ajax/load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
