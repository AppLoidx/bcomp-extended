
class Content extends React.Component {
  render() {
    return (

      <MainPage />
      )
  }
}

ReactDOM.render(
     <ReactRouterDOM.HashRouter>
        <div>
          <div >
            <ReactRouterDOM.Route exact path="/" component={MainPage}/>
            <ReactRouterDOM.Route exact path="/features" component={FeaturesPage}/>
            <ReactRouterDOM.Route exact path="/contributing" component={Contributing} />
            <ReactRouterDOM.Route exact path="/download" component={Download} />
          </div>
        </div>
      </ReactRouterDOM.HashRouter>
    ,
    document.getElementById('react-content')
)