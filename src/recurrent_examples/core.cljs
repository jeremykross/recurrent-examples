(ns recurrent-examples.core
  (:require
    recurrent.drivers.dom
    [recurrent-examples.examples.hello-world :as hello-world]
    [cemerick.url :as url]
    [recurrent.core :as recurrent :include-macros true]
    [ulmus.signal :as ulmus]))


(recurrent/defcomponent Main
  []
  (let [content (! hello-world/Main)]
    {:recurrent/dom-$
     (ulmus/map
       (fn [content]
         [:div {:id "main"}
          [:div {:class "menu"}
           [:div {:class "title"}
            [:h1 {} "Recurrent Examples"]]]
          [:div {:class "content"}
           content]])
       (:recurrent/dom-$ content))}))

(defn start!
  []
  (recurrent/start!
    Main
    {:recurrent/dom-$ (recurrent.drivers.dom/create! "app")}))
