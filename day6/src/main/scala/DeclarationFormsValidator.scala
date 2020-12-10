class DeclarationFormsValidator(forms: List[String]) {
    private var _affirmatives = 0

    def affirmatives : Int = _affirmatives

    def validate(): Unit = {
      var _forms: List[Set[Char]] = List.empty
      for(form <- forms) {
        if ( form.isEmpty ) {
          process(_forms)
          _forms = List.empty
        } else _forms :+= form.toCharArray.toSet
      }
      process(_forms)
    }

    private def process(forms: List[Set[Char]]): Unit = {
      _affirmatives += forms.reduce((a,b) => a.intersect(b)).size
    }

}
