(ns minilisp.env)

(defprotocol IEnv
  (get-value [this val-name])
  (set-value [this val-name value]
    "Set new-value to env"))

(defrecord Env [env-map outer-env]
  IEnv
  (get-value [this val-name]
    (let [[_ value] (find @env-map val-name)]
      (condp = value
        nil (if-some [env outer-env]
              (get-value outer-env val-name)
              (throw (ex-info "Undefined variable" {:key val-name :outer-env outer-env})))
        value)))
  (set-value [this val-name value]
    (reset! env-map (assoc @env-map val-name value))))

(defn make-Env
  [outer-env]
  (Env. (atom (hash-map)) outer-env))

(defn make-Env-with-kv
  [keys values outer-env]
  (let [env (make-Env outer-env)
        zipped (map list keys values)]
      (doseq [[key value] zipped]
        (set-value env key value))
      env))
