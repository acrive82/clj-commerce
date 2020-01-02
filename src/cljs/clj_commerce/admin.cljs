(ns clj-commerce.admin
  (:require
   [clj-commerce.admin.statistics :as stat]
   [clj-commerce.admin.eventssection :as eventsect]
   [clj-commerce.admin.searchform :as searchform]
   [markdown.core :refer [md->html]]
   [clojure.string :as string]))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href   uri
    }
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

(defn bread-crumbs []
  [:nav.breadcrumb {:aria-label "breadcrumbs"}
   [:ul
    [:li [:a {:href "../"} "Bulma 1"]]
    [:li [:a {:href "../"} "Bulma 2"]]
    [:li.is-active [:a {:href "../"} "Bulma 3"]]
    ]])


(defn hero-section []
  [:section.hero.is-info.welcome.is-small
   [:div.hero-body
    [:div.container
     [:h1.title "CLJ Commerce"]
     [:h2.sub-title "A Wonderful ECommerce written in Clojure"]]]])

(defn admin-page []
  [:div
   [bread-crumbs]
   [:section
    [hero-section]
    [stat/statistics-section]
    [:div.columns
     [:div.column.is-6
      [eventsect/eventslist-section]
     ]
     [:div.column.is-6
      [searchform/search-form "Prodotti"]
      [searchform/search-form "Ordini"]
      [:h2 "HELLOOO"]]]]
    ])
