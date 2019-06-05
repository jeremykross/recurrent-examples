(ns recurrent-examples.core
  (:require
    recurrent.drivers.dom
    [clojure.string :as string]
    [recurrent-examples.examples.hello-world :as hello-world]
    [recurrent-examples.examples.left-or-right :as left-or-right]
    [recurrent-examples.examples.todo :as todo]
    [cemerick.url :as url]
    [recurrent.core :as recurrent :include-macros true]
    [ulmus.signal :as ulmus]
    [ulmus.window :as window]))

(defn- hash->section
  [h]
  (if (not (empty? h))
    (keyword (string/replace h "#/" ""))
    :hello-world))

(recurrent/defcomponent Main
  []
  (let [current-example-$
        (ulmus/map hash->section window/hash-$)
        todo (! todo/TodoFRP)
        examples
        {:hello-world (:recurrent/dom-$ (! hello-world/Main))
         :left-or-right (:recurrent/dom-$ (! left-or-right/Main))
         :todo (:recurrent/dom-$ todo)}]
    {:recurrent/state-$
     (:recurrent/state-$ todo)

     :recurrent/dom-$
     (ulmus/map
       (fn [content]
         [:div {:id "main"}
          [:div {:class "menu"}
           [:div {:class "title"}
            [:h1 {} "Recurrent Examples"]
            [:a {:data-example "hello-world" :href "#/hello-world"} "Hello World"]
            [:a {:data-example "todo" :href "#/todo"} "Todo FRP"]
            [:a {:data-example "left-right" :href "#/left-or-right"} "Left or Right"]]]
          [:div {:class "main-content"}
           content]])
       (ulmus/choose (ulmus/start-with! 
                       (hash->section (.-hash (.-location js/window)))
                       current-example-$)
                     examples))}))

(defn start!
  []
  (recurrent/start!
    Main
    {:recurrent/dom-$ (recurrent.drivers.dom/render-into! "app")
     :recurrent/state-$ (recurrent.drivers.state/create-store! todo/initial-state)}))

(set! (.-onload js/window) start!)
