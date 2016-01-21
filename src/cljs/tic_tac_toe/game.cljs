(ns tic-tac-toe.game)

(defn init [n]
  {:player "X"
   :board  (vec (doall (repeat n (vec (doall (repeat n nil))))))})

(defn get-val [game x y]
  (nth (nth (:board game) y) x " "))

(defn update-player [player]
  (case player
    "X" "O"
    "O" "X"))

(defn make-move [game x y]
  (let [board (:board game)
        player (:player game)]
    (if (nil? (get-val game x y))
      {:player (update-player player)
       :board  (assoc board y (assoc (get board y) x player))}
      game)))