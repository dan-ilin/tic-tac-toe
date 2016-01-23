(ns tic-tac-toe.game)

(defn init [n]
  {:player true
   :board  (vec (doall (repeat n (vec (doall (repeat n nil))))))})

(defn get-val [game x y]
  (nth (nth (:board game) y) x " "))

(defn get-marker [bool]
  (if bool "X" "O")
  (case bool
    nil " "
    true "X"
    false "O"))

(defn update-player [player]
  (not player))

(defn make-move [game x y]
  (let [board (:board game)
        player (:player game)]
    (if (nil? (get-val game x y))
      {:player (update-player player)
       :board  (assoc board y (assoc (get board y) x player))}
      game)))