(ns tic-tac-toe.game)

(defn init [n]
  {:player true
   :board  (vec (doall (repeat n (vec (doall (repeat n nil))))))})

(defn get-val [game x y]
  (get x (get y (:board game))))

(defn make-move [game x y]
  (let [board (:board game)
        player (:player game)]
    (if (nil? (get-val game x y))
      {:player (not player)
       :board  (assoc board y (assoc (get board y) x (case player false "o"
                                                                  true "x")))})))