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
   [:.content {:box-shadow "-4px 0 32px rgba(0,0,0, 0.25)"
               :flex 1
               :padding "32px"}]])

(def Hello-World
  [:#hello-world
   ["input, h1, p" {:margin-bottom "16px"}]])

(def Left-Or-Right
  [:#left-or-right])

(def styles
  [Reset
   Hello-World
   Main])

(defn spit-styles!
  []
  (spit "resources/public/css/style.css" (garden/css styles)))

