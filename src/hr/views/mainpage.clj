(ns hr.views.mainpage
  (:require [hr.views.common :as common]
            [noir.content.getting-started])
  (:use [cheshire.core :only [generate-string]])
  (:use [noir.core :only [defpage]])
  (:use [hiccup.form :only [submit-button]])
  (:use [hiccup.element :only [link-to]])
  (:import [de.neuland.jade4j Jade4J]))

; does not look like a good idea
(defpage "/welcome" []
         (common/layout
           [:form.adddepartment {:data-bind "submit: addDepartment"}
             [:p "Add department: "]
             [:input {:data-bind "value: newDepartmentText" :placeholder "department name"}]
             [:button {:type "submit"} "Save"]
           ]
           [:ul.departments {:data-bind "foreach: departments, visible: departments().length > 0"}
            [:li 
             [:p {:data-bind "text: name"} ""]
             [:p {:data-bind "text: empcount"} ""]
             [:a {:data-bind "click: $parent.removeDepartment" :href "#"} "Delete"]
            ]
           ]
         ))

(defn read-template [name]
  (let [url (java.net.URL. (str "http://localhost:8080/templates/" name ".jade"))]
      (Jade4J/render url {})))

(defpage "/" [] (read-template "mainpage"))

  
  