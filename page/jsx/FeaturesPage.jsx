class FeaturesPage extends React.Component {

	componentDidMount(){
		$('#preview-img').fadeOut(0);
		$('#preview-img').one("load", function(){
			// 
			// $('#preview-img').animate({
			// 	top: "-=1000px";
			// }, 10);

			$('#preview-img').fadeIn(4000);


		})
		// $(document).ready(function(){
		// 	$('#preview-img').slideDown();

		// })
	}

	render() {
		return (
			<div >
					<div className = "mt-5 new-possibles">
					<div className = "new-possibles-inner">
					<h1 className="cover-heading ">Новые возможности</h1>
		    			<p className="lead">Раскройте для себя полный функционал</p>
		    			<p className="lead"></p>
		    			<img id="preview-img" className="img-fluid rounded" src="https://github.com/AppLoidx/bcomp-extended/raw/master/report/res/basic-view.png"></img>
		    		</div>
		    		</div>
		    	<div className="features-cards">
				<hr class="featurette-divider"></hr>

			    <div class="row featurette">
			      <div class="col-md-7">
			        <h2 class="featurette-heading">CLI <br></br><span class="text-muted">Встроенная консоль</span></h2>
			        <p class="lead">Теперь вам не надо запускать консоль отдельно от графической версии. Все соединено в одном моде</p>
			      </div>
			      <div class="col-md-5">
			        <img src="page/img/code-example.gif"class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 500x500"></img>
			      </div>
			    </div>

			    <hr class="featurette-divider"></hr>

			    <div class="row featurette">
			      <div class="col-md-7 order-md-2">
			        <h2 class="featurette-heading">Assembler <span class="text-muted">Подсвечиваемый ассемблер</span></h2>
			        <p class="lead">Подсветка синтаксиса редактора ассемблера.</p>
			      </div>
			      <div class="col-md-5 order-md-1">
			        <img src="page/img/assembler-example.png"class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-thumbnail img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 500x500"></img>
			      </div>
			    </div>

			    <hr class="featurette-divider"></hr>

			    <div class="row featurette">
			      <div class="col-md-7">
			        <h2 class="featurette-heading">Внешний вид <span class="text-muted">Настройка стиля</span></h2>
			        <p class="lead">Теперь вы можете изменить внешний вид эмулятора на предпочитаемый вами</p>
			      </div>
			      <div class="col-md-5">
			        <img src="page/img/theme-example.png"class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 500x500"></img>
			      </div>
			    </div>

			    <hr class="featurette-divider"></hr>

			    <div class="row featurette">
			      <div class="col-md-7 order-md-2">
			        <h2 class="featurette-heading">Инструменты <br></br><span class="text-muted">Полезные надстройки</span></h2>
			        <p class="lead">Прямой доступ к памяти и дебаггер в вашем распоряжении</p>
			      </div>
			      <div class="col-md-5 order-md-1">
			        <img src="page/img/tools-example.png"class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 500x500"></img>
			      </div>
			    </div>

			    <hr class="featurette-divider"></hr>
			    </div>
			</div>
			)
	}
}