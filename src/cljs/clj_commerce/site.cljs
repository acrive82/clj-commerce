(ns clj-commerce.site
  (:require
    [reagent.core :as r]
    [clj-commerce.ajax :as ajax]
    [reitit.core :as reitit]
    [clojure.string :as string])
  )


(defn home-page []
  [:section.section>div.container>div.content
   [:div
    [:h1 "Hello"]]])