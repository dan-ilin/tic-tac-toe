(ns tic-tac-toe.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]
            [tic-tac-toe.game :as game]))

;; -------------------------
;; Views

(def n (reagent/atom 3))
(def game (reagent/atom (game/init @n)))

(defn game-board []
  @game @n
  [:div
   [:table
    [:thead
     [:h1
      (if (:winner? @game)
        (str (:player @game) " wins!")
        "TicTacToe")]
     [:tr
      [:th
       [:p (str "X: " (:X (:score @game)))]]
      [:th
       [:input {:style    {:backgroundColor "#ffffff"
                           :border-color    "#bbbbbb"
                           :border-width    "0.1em"
                           :border-style    "solid"}
                :type     "button"
                :value    "Reset"
                :on-click #(reset! game (game/init @n))}]

       [:input {:style    {:backgroundColor "#ffffff"
                           :border-color    "#bbbbbb"
                           :border-width    "0.1em"
                           :border-style    "solid"}
                :type     "button"
                :value    "Next Game"
                :disabled (not (:winner? @game))
                :on-click #(reset! game (game/reset-board @n @game))}]]
      [:th
       [:p (str "O: " (:O (:score @game)))]]]]
    [:tbody
     (for [y (range 0 (:n @game))]
       ^{:key (str "row" + y)}
       [:tr
        (for [x (range 0 (:n @game))]
          ^{:key (str "cell" + x + y)}
          [:td
           [:input {:value         (game/get-val @game x y)
                    :type          "button"
                    :disabled      (:winner? @game)
                    :style         {:font-size       "3em"
                                    :width           "3em"
                                    :height          "3em"
                                    :backgroundColor "#ffffff"
                                    :border-color    "#bbbbbb"
                                    :border-width    "0.1em"
                                    :border-style    (if (nil? (game/get-val @game x y))
                                                       "solid"
                                                       "groove")}
                    :on-mouse-over #(js/console.log x y (game/get-val @game x y))
                    :on-click      #(js/console.log (str (swap! game game/make-move x y)))}]])])]]])

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
