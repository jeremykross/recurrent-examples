(ns recurrent-examples.examples.hello-world
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
         [:div {:id "hello-world"}
          [:h1 {} "Hello World"]
          [:p {} "Try changing the value in the text input to see the div below update automatically."]
          input-dom
          [:div {:class "message"}
           (str (or message "Howdy") " " input-value)]])
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

