(ns hr.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-js include-css html5]]
        [hiccup.form]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "hr"]
               (include-css "/css/reset.css")
               (include-css "/css/hr.css")
               (include-js "/js/jquery-1.8.1.min.js")
               (include-js "/js/knockout-2.1.0.debug.js")
               (include-js "/js/mainpage.js")
               ]
              [:body
               [:div#wrapper
                content]]))
