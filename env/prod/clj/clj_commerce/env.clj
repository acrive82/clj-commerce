(ns clj-commerce.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[clj-commerce started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[clj-commerce has shut down successfully]=-"))
   :middleware identity})
