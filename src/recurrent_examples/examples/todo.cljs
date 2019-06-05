(ns recurrent-examples.examples.todo
  (:require
    recurrent.drivers.dom
    [clojure.string :as string]
    [recurrent.core :as recurrent :include-macros true]
    [recurrent.drivers.state :as state]
    [ulmus.signal :as ulmus]))


(def initial-state
  {:todos [{:text "Make a todo app"
            :completed? true}
           {:text "????"
            :completed? false}
           {:text "Profit"
            :completed? false}]})

(recurrent/defcomponent TodoInput
  []
  (let [return-$
        (ulmus/filter
          (fn [e] (= (.-keyCode e) 13))
          ($ :recurrent/dom-$ "input" "keypress"))

        value-$
        (ulmus/start-with! ""
          (ulmus/merge
            (ulmus/map
              (fn [e]
                (.-value (.-target e)))
              ($ :recurrent/dom-$ "input" "input"))
            (ulmus/map
              (constantly "")
              return-$)))

        submit-$
        (ulmus/sample-on
          (ulmus/map
            first
            (ulmus/slice 2 value-$))
          return-$)]

    {:clear-all-$
     ($ :recurrent/dom-$ ".clear-all" "click")

     :submit-$
     submit-$ 

     :recurrent/dom-$
     (ulmus/map
       (fn [value]
         [:div {:id "what-needs-done"}
          [:div {:class "clear-all"} "<"]
          [:input {:type "text"
                   :placeholder "What needs to be done?"
                   :value value}]])
       value-$)}))

(recurrent/defcomponent Todo
  [mode-$]
  {:close-$ 
   (ulmus/sample-on
     ($ :recurrent/state-$)
     (ulmus/map
       (fn [e] (.stopPropagation e))
       ($ :recurrent/dom-$ ".close" "click")))

   :recurrent/dom-$
   (ulmus/map
     (fn [[todo mode]]
       [:div (merge 
               {:class
                (str "todo "
                     (if (:completed? todo) "completed"))}
               (if (or (and (= mode :outstanding)
                            (:completed? todo))
                       (and (= mode :completed)
                            (not (:completed? todo))))
                 {:style {:display "none"}}))
        (:text todo)
        [:div {:class "close"} "X"]])
     (ulmus/zip
       ($ :recurrent/state-$)
       mode-$))

   :recurrent/state-$
   (ulmus/map
     (fn []
       #(assoc % :completed? (not (:completed? %))))
     ($ :recurrent/dom-$ :root "click"))})

(recurrent/defcomponent ViewBar
  []
  (let [mode-$
        (ulmus/start-with! :all
          (ulmus/map
            (fn [e]
              (keyword (.getAttribute (.-target e) "data-mode")))
            ($ :recurrent/dom-$ ".mode" "click")))

        mode-fn 
        (fn [selected-mode mode]
          [:div {:data-mode mode
                 :class
                 (str "mode "
                      (if (= selected-mode (keyword mode))
                        "selected"))}
           (string/capitalize mode)])]

    {:mode-$
     mode-$

     :recurrent/dom-$
     (ulmus/map
       (fn [mode]
         (let [make-mode (partial mode-fn mode)]
           [:div {:class "view-bar"}
            [:h4 "View"]
            (make-mode "all")
            (make-mode "outstanding")
            (make-mode "completed")]))
       mode-$)}))

(recurrent/defcomponent TodoFRP
  []
  (let [todo-input (! TodoInput)
        view-bar (! ViewBar)
        todos (! (state/isolate
                   [:todos]
                   (state/collection Todo))
                 (:mode-$ view-bar))]
    {:recurrent/dom-$
     (ulmus/map
       (fn [[todo-input todos view-bar]]
         [:div {:id "todo-frp" :class "example"}
          [:h1 "Todo FRP"]
          [:a {:href "https://github.com/jeremykross/recurrent-examples/blob/master/src/recurrent_examples/examples/todo.cljs"
               :target "_blank"
               :rel "noopener noreferrer"} "Source"]
          [:div {:class "content"}
            todo-input
            todos
            view-bar]])
       (ulmus/zip
         (:recurrent/dom-$ todo-input)
         (:recurrent/dom-$ todos)
         (:recurrent/dom-$ view-bar)))

     :recurrent/state-$ 
     (ulmus/merge
       (:recurrent/state-$ todos)
       (ulmus/map
         (fn [new-todo]
           (fn [state]
             (update
               state :todos
               conj
               {:text new-todo
                :completed? false})))
         (:submit-$ todo-input))
       (ulmus/map
         (fn [remove-todo]
           (fn [state]
             (update
               state :todos
               (fn [todos]
                 (into
                   []
                   (remove #(= % remove-todo) todos))))))
         (ulmus/pickmerge :close-$ (:elements-$ todos)))
       (ulmus/map
         (fn []
           (fn [state]
             (update
               state :todos
               (fn [todos]
                 (mapv (fn [todo] (assoc
                                    todo
                                    :completed? true))
                       todos)))))
         (:clear-all-$ todo-input)))}))


(defn start!
  []
  (recurrent/start!
    TodoFRP
    {:recurrent/dom-$
     (recurrent.drivers.dom/render-into! "app")
     :recurrent/state-$
     (recurrent.drivers.state/create-store! initial-state)}))

