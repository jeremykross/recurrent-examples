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
          [:input {:class "my-input" :type "text" :value v}]])
       value-$)}))

(recurrent/defcomponent Main
  [message]
  (let [input (! Input "Partner")]
    {:recurrent/dom-$
     (ulmus/map
       (fn [[input-dom input-value]]
         [:div {:id "hello-world" :class "example"}
          [:h1 {} "Hello World"]
          [:a {:href "https://github.com/jeremykross/recurrent-examples/blob/master/src/recurrent_examples/examples/hello_world.cljs"
               :target "_blank"
               :rel "noopener noreferrer"} "Source"]
          [:p "Try changing the value in the text input to see the div below update automatically."]
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
     (recurrent.drivers.dom/render-into! "app")}
    "Howdy"))

