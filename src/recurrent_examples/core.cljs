(ns recurrent-examples.core
  (:require
    recurrent-examples.hello-world
    [cemerick.url :as url]
    [recurrent.core :as recurrent :include-macros true]
    [ulmus.signal :as ulmus]))


(recurrent/defcomponent Main
  [])

(defn start!
  []
  (recurrent-examples.hello-world/start!))
