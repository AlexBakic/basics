(ns basics.core
  (:require [clj-http.client :as client]
            [org.httpkit.server :refer [run-server]]
            [cheshire.core :as cheshire]))

(defn uk-covid-deaths
  []
  (let [results (-> "https://covid-api.mmediagroup.fr/v1/cases?country=United Kingdom"
                   client/get
                   :body
                   cheshire/parse-string)]
    results))

(defn england-deaths
  [covid-map]
  (let [england (get covid-map "England")
        deaths (get england "deaths")
        time (get england "updated")]
    (str "There have been " deaths " deaths in the UK as of " time)))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body (england-deaths (uk-covid-deaths))})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-server app {:port 8080}))


