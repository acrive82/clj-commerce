(ns clj-commerce.admin
  (:require
    [day8.re-frame.http-fx]
    [reagent.core :as r]
    [re-frame.core :as rf]
    [goog.events :as events]
    [goog.history.EventType :as HistoryEventType]
    [markdown.core :refer [md->html]]
    [clj-commerce.ajax :as ajax]
    [clj-commerce.events]
    [reitit.core :as reitit]
    [clojure.string :as string]))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href   uri
    :class (when (= page @(rf/subscribe [:page])) :is-active)}
   title])

(defn navbar-admin [title]
  [:nav.navbar.is-white
   [:div.container
    [:div.navbar-brand
     [:a.navbar-item.brand-text {:href "https://www.google.it"} (if (= nil title) "SET ME" title )]
     [:div.navbar-burger.burger {:data-target "navMenu"}
      [:span]
      [:span]
      [:span]
      ]]
    [:div.navbar-menu {:id "navMenu" }
     [nav-link "#/" "Home" :home]
     [nav-link "#/admin" "Admin" :admin]
     [nav-link "#/about" "About" :about]
     ]]])

(defn aside-menu []
  [:aside.menu.is-hidden-mobile
   [:p.menu-label "General"]
   [:ul.menu-list
    [:li [:a.is-active "Dashboard"]]
    [:li [:a.is-active "Customers"]]
    [:li [:a.is-active "Other"]]]
   [:p.menu-label "Amministration"]
   [:ul.menu-list
    [:li [:a "Team Setting"]]
    [:li
     [:a "Manage your team"]
     [:ul
      [:li [:a "Member"]]
      [:li [:a "Plugin"]]
      [:li [:a "Ciao"]]
      ]]
    ]])

(defn content-body []
  [:h2 "Hello word"])

(defn admin-page []
  [:div.container
   [:div.columns
    [:div.column.is-3
     aside-menu]
    [:div.column.is-9 content-body]]])