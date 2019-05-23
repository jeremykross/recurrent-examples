(ns recurrent-examples.hello-world
  (:require 
    recurrent.drivers.dom
    [ulmus.signal :as ulmus]
    [recurrent.core :as recurrent :include-macros true]))

(recurrent/defcomponent Input
  [initial-value]
  (let [value-$ 
        (ulmus/start-with!
          initial-value
          (ulmus/map
            (fn [e] 
              (.-value (.-target e)))
            ($ :recurrent/dom-$ ".my-input" "input")))]

    {:value-$ value-$

     :recurrent/dom-$
     (ulmus/map
       (fn [v] 
         [:div {}
          [:input {:class "my-input" :type "text" :defaultValue initial-value}]])
       value-$)}))

(recurrent/defcomponent Main
  [message]
  (let [input (! Input "Partner")]
    {:recurrent/dom-$
     (ulmus/map
       (fn [[input-dom input-value]]
         [:div {}
          input-dom
          [:br]
          (str message " " input-value)])
       (ulmus/zip
         (:recurrent/dom-$ input)
         (:value-$ input)))}))
       
(defn start!
  []
  (recurrent/start!
    Main
    {:recurrent/dom-$
     (recurrent.drivers.dom/create! "app")}
    "Howdy"))

