(ns tic-tac-toe.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]
            [tic-tac-toe.game :as game]))

;; -------------------------
;; Views

(def n 3)
(def game (reagent/atom (game/init n)))

(defn game-board []
  @game
  [:div
   [:table
    [:tbody
     (for [y (range 0 n)]
       ^{:key (str "row" + y)}
       [:tr
        (for [x (range 0 n)]
          ^{:key (str "cell" + x + y)}
          [:td
           [:input {:value         (game/get-marker (game/get-val @game x y))
                    :type          "button"
                    :style         {:font-size       (str (max 2 (/ 9 n)) "em")
                                    :min-width       "1.5em"
                                    :width           (str (/ 9 n) "em")
                                    :min-height      "1.5em"
                                    :height          (str (/ 9 n) "em")
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
