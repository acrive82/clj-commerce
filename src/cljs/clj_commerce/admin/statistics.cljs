(ns clj-commerce.admin.statistics )

(defn statistics-section []
  [:section.info-tiles
   [:div.tile.is-ancestor.has-text-centered
    [:div.tile.is-parent
     [:article.tile.is-child.box
      [:p.title "439k"]
      [:p.subtitle "Ordini"]]]
    [:div.tile.is-parent
     [:article.tile.is-child.box
      [:p.title "20k"]
      [:p.subtitle "Utenti"]]]
    [:div.tile.is-parent
     [:article.tile.is-child.box
      [:p.title "10"]
      [:p.subtitle "Ordini effettuati oggi"]]]
    [:div.tile.is-parent
     [:article.tile.is-child.box
      [:p.title "12"]
      [:p.subtitle "Utenti online"]]]
    ]])
