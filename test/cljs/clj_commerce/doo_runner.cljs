(ns clj-commerce.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [clj-commerce.core-test]))

(doo-tests 'clj-commerce.core-test)

