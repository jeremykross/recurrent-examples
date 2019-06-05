(ns recurrent-examples.style
  (:require
    garden.selectors
    [garden.core :as garden]
    [garden.def :refer [defkeyframes]]))

(def Reset
  [[:html :body {:width "100%"
                 :height "100%"}]
   [:body {:box-sizing "border-box"
           :font-style "sans-serif"
           :font-family "'Rubik', sans-serif"
           :margin 0
           :padding 0
           :width "100%"
           :height "100%"}]
   [:#app {:width "100%"
           :height "100%"}]
   [:body 
    [:* {:box-sizing "border-box"
         :margin 0
         :padding 0}]
    [:a {:color "cornflowerblue"
         :text-decoration "none"}]
    [:h1 {:font-size "24px"
          :font-weight "bold"}]]
   ["input[type=checkbox]" {:visibility "hidden"}]
   [:.example
     [:a {:display "block"
          :margin-bottom "16px"}]
     [:.message {:font-style "italic"
                  :font-weight "bold"}]
     [:.mono {:background "black"
              :border-radius "4px"
              :color "lime"
              :font-family "monospace"
              :padding "16px"}]]])



(def Main
  [:#main {:display "flex"
           :height "100%"
           :width "100%"}
   [:.menu {:width "296px"}
    [:a {:display "block"
         :margin-bottom "8px"}]
    [:.title {:padding "16px"
              :text-align "center"}]]
   [:.main-content {:box-shadow "-4px 0 32px rgba(0,0,0, 0.25)"
                    :flex 1
                    :padding "32px"}]])

(def HelloWorld
  [:#hello-world
   ["input, h1, p" {:margin-bottom "16px"}]])

(def LeftOrRight
  [:#left-or-right])

(def TodoFRP
  [:#todo-frp
   {:display "flex"
    :flex-direction "column"
    :margin-top "16px"}
   [:.content {:width "512px"}
    [:.todo {:border "1px solid lightgrey"
             :cursor "pointer"
             :display "flex"
             :padding "16px"
             :font-size "1.2rem"
             :margin-left "42px"
             :transition "opacity 500ms"}
     [:&.completed {:opacity 0.5
                    :text-decoration "line-through"}]
     [:.close {:display "none"
               :margin-left "auto"
               :text-align "right"}]]
    [".todo:hover .close" {:display "block"}]
    [:.view-bar {:display "flex"
                 :margin "16px 0"}
     [:h4 {:margin-left "42px"
           :margin-right "16px"}]
     [:div {:cursor "pointer"
            :margin-right "8px"}
      [:&.selected {:font-weight "bold"}]]]
    [:#what-needs-done
     {:display "flex"}
     [:.clear-all {:cursor "pointer"
                   :font-size "1.3rem"
                   :margin "16px"
                   :transform "rotate(-90deg)"}]
     [:input {:padding "16px"
              :font-size "1.3rem"
              :width "100%"}]]]])

(def styles
  [Reset
   HelloWorld
   LeftOrRight
   TodoFRP
   Main])

(defn spit-styles!
  []
  (spit "resources/public/css/style.css" (garden/css styles)))

