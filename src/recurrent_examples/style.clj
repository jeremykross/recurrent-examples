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
   [:body [:* {:box-sizing "border-box"
               :margin 0
               :padding 0}]]
   ["input[type=checkbox]" {:visibility "hidden"}]])

(def styles
  [Reset])

(defn spit-styles!
  []
  (spit "resources/public/css/style.css" (garden/css styles)))

