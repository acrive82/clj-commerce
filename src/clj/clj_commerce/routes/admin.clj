(ns clj-commerce.routes.admin
  (:require
    [clj-commerce.layout :as layout]
    [clj-commerce.db.core :as db]
    [clojure.java.io :as io]
    [clj-commerce.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response]))

(defn admin-page [request]
  (layout/render request "admin.html"))

(defn admin-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/admin" {:get admin-page}]])
