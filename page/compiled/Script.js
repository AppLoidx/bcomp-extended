
class Content extends React.Component {
  render() {
    return React.createElement(MainPage, null);
  }
}

ReactDOM.render(React.createElement(
  ReactRouterDOM.HashRouter,
  null,
  React.createElement(
    "div",
    null,
    React.createElement(
      "div",
      null,
      React.createElement(ReactRouterDOM.Route, { exact: true, path: "/", component: MainPage }),
      React.createElement(ReactRouterDOM.Route, { exact: true, path: "/features", component: FeaturesPage }),
      React.createElement(ReactRouterDOM.Route, { exact: true, path: "/contributing", component: Contributing }),
      React.createElement(ReactRouterDOM.Route, { exact: true, path: "/download", component: Download })
    )
  )
), document.getElementById('react-content'));