(ns clj-commerce.handler
  (:require
    [clj-commerce.middleware :as middleware]
    [clj-commerce.layout :refer [error-page]]
    [clj-commerce.routes.home :refer [home-routes]]
    [clj-commerce.routes.admin :refer [admin-routes]]
    [reitit.ring :as ring]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.webjars :refer [wrap-webjars]]
    [clj-commerce.env :refer [defaults]]
    [mount.core :as mount]))

(mount/defstate init-app
                :start ((or (:init defaults) (fn [])))
                :stop ((or (:stop defaults) (fn []))))

(mount/defstate app-routes
                :start
                (ring/ring-handler
                  (ring/router
                    [(home-routes)
                     (admin-routes)])
                  (ring/routes
                    (ring/create-resource-handler
                      {:path "/"})
                    (wrap-content-type
                      (wrap-webjars (constantly nil)))
                    (ring/create-default-handler
                      {:not-found
                       (constantly (error-page {:status 404, :title "404 - Page not found"}))
                       :method-not-allowed
                       (constantly (error-page {:status 405, :title "405 - Not allowed"}))
                       :not-acceptable
                       (constantly (error-page {:status 406, :title "406 - Not acceptable"}))}))))

(defn app []
  (middleware/wrap-base #'app-routes))
