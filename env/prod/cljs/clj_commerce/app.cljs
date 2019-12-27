(ns clj-commerce.app
  (:require [clj-commerce.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
