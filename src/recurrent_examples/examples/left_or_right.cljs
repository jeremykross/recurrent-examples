(ns recurrent-examples.examples.left-or-right
  (:require 
    recurrent.drivers.dom
    [ulmus.mouse :as mouse]
    [ulmus.signal :as ulmus]
    [ulmus.window :as window]
    [recurrent.core :as recurrent :include-macros true]))

(recurrent/defcomponent Main
  []
  (let [mouse-x-$
        (ulmus/map first mouse/position-$)
        left-right-$
        (ulmus/map
          (fn [[mouse-x win-width]]
            (if (> mouse-x (/ win-width 2))
              :right
              :left))
          (ulmus/zip
            mouse-x-$
            window/width-$))]
    {:recurrent/dom-$
     (ulmus/map
       (fn [left-or-right]
         [:div {:id "left-or-right" :class "example"}
          [:h1 {} "Left or Right"]
          [:a {:href "https://github.com/jeremykross/recurrent-examples/blob/master/src/recurrent_examples/examples/left_or_right.cljs"
               :target "_blank"
               :rel "noopener noreferrer"} "Source"]
          [:p {} "The label below will update as the mouse moves from the left to the right side of the window."]
          [:p {:class "mono"} (str left-or-right)]])
       left-right-$)}))
       
(defn start!
  []
  (recurrent/start!
    Main
    {:recurrent/dom-$
     (recurrent.drivers.dom/create! "app")}))

