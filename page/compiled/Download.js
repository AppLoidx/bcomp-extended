class Download extends React.Component {
  render() {
    return React.createElement(
      "div",
      { "class": "card-deck mb-3 text-center" },
      React.createElement(
        "div",
        { "class": "card mb-4 shadow-sm" },
        React.createElement(
          "div",
          { "class": "card-header" },
          React.createElement(
            "h4",
            { "class": "my-0 font-weight-normal text-dark" },
            "Stable"
          )
        ),
        React.createElement(
          "div",
          { "class": "card-body" },
          React.createElement(
            "ul",
            { "class": "list-unstyled mt-3 mb-4 text-dark" },
            React.createElement(
              "li",
              null,
              "\u0421\u0442\u0430\u0431\u0438\u043B\u044C\u043D\u0430\u044F \u0432\u0435\u0440\u0441\u0438\u044F"
            ),
            React.createElement(
              "li",
              null,
              "\u0432 \u0432\u0438\u0434\u0435 \u0441\u043E\u0431\u0440\u0430\u043D\u043D\u043E\u0433\u043E"
            ),
            React.createElement(
              "li",
              null,
              "JAR-\u0444\u0430\u0439\u043B\u0430"
            )
          ),
          React.createElement(
            "a",
            { href: "https://github.com/AppLoidx/bcomp-extended/releases/latest", "class": "btn btn-lg btn-block btn-primary" },
            "Download JAR"
          )
        )
      ),
      React.createElement(
        "div",
        { "class": "card mb-4 shadow-sm" },
        React.createElement(
          "div",
          { "class": "card-header" },
          React.createElement(
            "h4",
            { "class": "my-0 font-weight-normal text-dark" },
            "Beta"
          )
        ),
        React.createElement(
          "div",
          { "class": "card-body" },
          React.createElement(
            "ul",
            { "class": "list-unstyled mt-3 mb-4 text-dark" },
            React.createElement(
              "li",
              null,
              "\u0412\u043E\u0437\u043C\u043E\u0436\u043D\u044B \u0431\u0430\u0433\u0438"
            ),
            React.createElement(
              "li",
              null,
              "\u041D\u0435\u0441\u043A\u043E\u043B\u044C\u043A\u043E \u0434\u043E\u043F\u043E\u043B\u043D\u0438\u0442\u0435\u043B\u044C\u043D\u044B\u0445 \u0444\u0443\u043D\u043A\u0446\u0438\u0439"
            )
          ),
          React.createElement(
            "a",
            { href: "https://github.com/AppLoidx/bcomp-extended/releases/tag/1.5.0-beta", "class": "btn btn-lg btn-block btn-primary" },
            "Download Beta"
          )
        )
      )
    );
  }
}