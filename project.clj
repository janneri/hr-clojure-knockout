(defproject hr "0.1.0-SNAPSHOT"
            :description "HR application with clojure"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [cheshire "4.0.0"]
                           [de.neuland/jade4j "0.2.16"]
                           [noir "1.3.0-beta3"]]
            :repositories {"jade4j-releases" "https://github.com/neuland/jade4j/raw/master/releases"}
            :main hr.server)
