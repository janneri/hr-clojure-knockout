(ns hr.server
  (:use [cheshire.core :only [parse-string]])
  (:require [noir.server :as server]))

(server/load-views-ns 'hr.views)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'hr})))

(defn add-body [handler]
  (fn [req]
    (let [neue (if (= "application/json" (get-in req [:headers "content-type"]))
               (update-in req [:params] assoc :json (parse-string (slurp (:body req)) true))
                req)]
      (handler neue))))

(server/add-middleware add-body)
