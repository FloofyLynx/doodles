(ns test-sketch.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn -main [& args]
  (println "Hi"))

(defn flower [t]
  (let [r (* 200 (q/sin t) (q/cos t))]
    [(* r (q/sin (* t 0.4)))
    (* r (q/cos (* t 0.2)))
    ]
  )
)

(defn random [t]
  (let [r (/ (q/sin t) (q/cos t))]
    [(* (q/cos r) (mod t 150))
    (* (q/sin r) (mod t 150))]
  )
)

(defn draw-plot [f from to step]
                    ; the arrow plugs the result of the previous item into the last argument for the next item
  (doseq [two-points (->> (range from to step)
                          (map f)
                          (partition 2 1))] ; breaks list into sequence of list of 2 items each, stepping 1 each time
    (apply q/line two-points)))

(defn draw []
  (q/color-mode :hsb)
  ; moves origin to center of the sketch
  ; default is top left
  (q/stroke 255 255 255)
  (q/with-translation [(/ (q/width) 2) (/  (q/height) 2)]
    (let [t (/ (q/frame-count) 10)]
      (q/with-stroke [(mod (* t 10) 255) (q/saturation (q/current-stroke)) (q/brightness (q/current-stroke))]
        (q/line (flower t)
                (flower (+ t 0.1)))))))

(defn setup []

  (q/frame-rate 60)
  (q/background 255))

(q/defsketch testing
  :size [300 300]
  :setup setup
  :draw draw)