class Download extends React.Component {
	render() {
		return (
			<div class="card-deck mb-3 text-center">

    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal text-dark">Stable</h4>
      </div>
      <div class="card-body">
        <ul class="list-unstyled mt-3 mb-4 text-dark">
          <li>Стабильная версия</li>
          <li>в виде собранного</li>
          <li>JAR-файла</li>

        </ul>
        <a href="https://github.com/AppLoidx/bcomp-extended/releases/latest"  class="btn btn-lg btn-block btn-primary">Download JAR</a>
      </div>
    </div>
    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal text-dark">Beta</h4>
      </div>
      <div class="card-body">
        <ul class="list-unstyled mt-3 mb-4 text-dark">
          <li>Возможны баги</li>
          <li>Несколько дополнительных функций</li>
        </ul>
        <a href="https://github.com/AppLoidx/bcomp-extended/releases/tag/1.5.0-beta" class="btn btn-lg btn-block btn-primary">Download Beta</a>
      </div>
    </div>
  </div>
			)
	}
}