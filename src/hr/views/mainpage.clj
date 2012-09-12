(ns hr.views.welcome
  (:require [hr.views.common :as common]
            [noir.content.getting-started])
  (:use [cheshire.core :only [generate-string]])
  (:use [noir.core :only [defpage]])
  (:use [hiccup.form :only [submit-button]])
  (:use [hiccup.element :only [link-to]]))


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
