(ns tic-tac-toe.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]
            [tic-tac-toe.game :as game]))

;; -------------------------
;; Views

(def n 3)

(defn game-board []
  (let [game (reagent/atom (game/init n))]
    [:div
     [:table
      [:tbody
       (for [y (range 0 n)]
         ^{:key (str "row" + y)}
         [:tr
          (for [x (range 0 n)]
            ^{:key (str "cell" + x + y)}
            [:td
             [:input {:type     "button"
                      :value    (game/get-val @game x y)
                      :style    {:font-size       "8em"
                                 :backgroundColor "#deadbeef"
                                 :width           "1em"
                                 :height          "1em"
                                 :text-align      "center"
                                 :border-style    "solid"}
                      :on-click #(js/console.log (str (swap! game game/make-move x y)))}]])])]]]))

(defn game-page []
  [:div
   (game-board)])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
                    (session/put! :current-page #'game-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!)
  (accountant/dispatch-current!)
  (mount-root))
