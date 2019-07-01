class FeaturesPage extends React.Component {

			componentDidMount() {
						$('#preview-img').fadeOut(0);
						$('#preview-img').one("load", function () {
									// 
									// $('#preview-img').animate({
									// 	top: "-=1000px";
									// }, 10);

									$('#preview-img').fadeIn(4000);
						});
						// $(document).ready(function(){
						// 	$('#preview-img').slideDown();

						// })
			}

			render() {
						return React.createElement(
									'div',
									null,
									React.createElement(
												'div',
												{ className: 'mt-5 new-possibles' },
												React.createElement(
															'div',
															{ className: 'new-possibles-inner' },
															React.createElement(
																		'h1',
																		{ className: 'cover-heading ' },
																		'\u041D\u043E\u0432\u044B\u0435 \u0432\u043E\u0437\u043C\u043E\u0436\u043D\u043E\u0441\u0442\u0438'
															),
															React.createElement(
																		'p',
																		{ className: 'lead' },
																		'\u0420\u0430\u0441\u043A\u0440\u043E\u0439\u0442\u0435 \u0434\u043B\u044F \u0441\u0435\u0431\u044F \u043F\u043E\u043B\u043D\u044B\u0439 \u0444\u0443\u043D\u043A\u0446\u0438\u043E\u043D\u0430\u043B'
															),
															React.createElement('p', { className: 'lead' }),
															React.createElement('img', { id: 'preview-img', className: 'img-fluid rounded', src: 'https://github.com/AppLoidx/bcomp-extended/raw/master/report/res/basic-view.png' })
												)
									),
									React.createElement(
												'div',
												{ className: 'features-cards' },
												React.createElement('hr', { 'class': 'featurette-divider' }),
												React.createElement(
															'div',
															{ 'class': 'row featurette' },
															React.createElement(
																		'div',
																		{ 'class': 'col-md-7' },
																		React.createElement(
																					'h2',
																					{ 'class': 'featurette-heading' },
																					'CLI ',
																					React.createElement('br', null),
																					React.createElement(
																								'span',
																								{ 'class': 'text-muted' },
																								'\u0412\u0441\u0442\u0440\u043E\u0435\u043D\u043D\u0430\u044F \u043A\u043E\u043D\u0441\u043E\u043B\u044C'
																					)
																		),
																		React.createElement(
																					'p',
																					{ 'class': 'lead' },
																					'\u0422\u0435\u043F\u0435\u0440\u044C \u0432\u0430\u043C \u043D\u0435 \u043D\u0430\u0434\u043E \u0437\u0430\u043F\u0443\u0441\u043A\u0430\u0442\u044C \u043A\u043E\u043D\u0441\u043E\u043B\u044C \u043E\u0442\u0434\u0435\u043B\u044C\u043D\u043E \u043E\u0442 \u0433\u0440\u0430\u0444\u0438\u0447\u0435\u0441\u043A\u043E\u0439 \u0432\u0435\u0440\u0441\u0438\u0438. \u0412\u0441\u0435 \u0441\u043E\u0435\u0434\u0438\u043D\u0435\u043D\u043E \u0432 \u043E\u0434\u043D\u043E\u043C \u043C\u043E\u0434\u0435'
																		)
															),
															React.createElement(
																		'div',
																		{ 'class': 'col-md-5' },
																		React.createElement('img', { src: 'page/img/code-example.gif', 'class': 'bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto', width: '500', height: '500', xmlns: 'http://www.w3.org/2000/svg', preserveAspectRatio: 'xMidYMid slice', focusable: 'false', role: 'img', 'aria-label': 'Placeholder: 500x500' })
															)
												),
												React.createElement('hr', { 'class': 'featurette-divider' }),
												React.createElement(
															'div',
															{ 'class': 'row featurette' },
															React.createElement(
																		'div',
																		{ 'class': 'col-md-7 order-md-2' },
																		React.createElement(
																					'h2',
																					{ 'class': 'featurette-heading' },
																					'Assembler ',
																					React.createElement(
																								'span',
																								{ 'class': 'text-muted' },
																								'\u041F\u043E\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u043C\u044B\u0439 \u0430\u0441\u0441\u0435\u043C\u0431\u043B\u0435\u0440'
																					)
																		),
																		React.createElement(
																					'p',
																					{ 'class': 'lead' },
																					'\u041F\u043E\u0434\u0441\u0432\u0435\u0442\u043A\u0430 \u0441\u0438\u043D\u0442\u0430\u043A\u0441\u0438\u0441\u0430 \u0440\u0435\u0434\u0430\u043A\u0442\u043E\u0440\u0430 \u0430\u0441\u0441\u0435\u043C\u0431\u043B\u0435\u0440\u0430.'
																		)
															),
															React.createElement(
																		'div',
																		{ 'class': 'col-md-5 order-md-1' },
																		React.createElement('img', { src: 'page/img/assembler-example.png', 'class': 'bd-placeholder-img bd-placeholder-img-lg featurette-image img-thumbnail img-fluid mx-auto', width: '500', height: '500', xmlns: 'http://www.w3.org/2000/svg', preserveAspectRatio: 'xMidYMid slice', focusable: 'false', role: 'img', 'aria-label': 'Placeholder: 500x500' })
															)
												),
												React.createElement('hr', { 'class': 'featurette-divider' }),
												React.createElement(
															'div',
															{ 'class': 'row featurette' },
															React.createElement(
																		'div',
																		{ 'class': 'col-md-7' },
																		React.createElement(
																					'h2',
																					{ 'class': 'featurette-heading' },
																					'\u0412\u043D\u0435\u0448\u043D\u0438\u0439 \u0432\u0438\u0434 ',
																					React.createElement(
																								'span',
																								{ 'class': 'text-muted' },
																								'\u041D\u0430\u0441\u0442\u0440\u043E\u0439\u043A\u0430 \u0441\u0442\u0438\u043B\u044F'
																					)
																		),
																		React.createElement(
																					'p',
																					{ 'class': 'lead' },
																					'\u0422\u0435\u043F\u0435\u0440\u044C \u0432\u044B \u043C\u043E\u0436\u0435\u0442\u0435 \u0438\u0437\u043C\u0435\u043D\u0438\u0442\u044C \u0432\u043D\u0435\u0448\u043D\u0438\u0439 \u0432\u0438\u0434 \u044D\u043C\u0443\u043B\u044F\u0442\u043E\u0440\u0430 \u043D\u0430 \u043F\u0440\u0435\u0434\u043F\u043E\u0447\u0438\u0442\u0430\u0435\u043C\u044B\u0439 \u0432\u0430\u043C\u0438'
																		)
															),
															React.createElement(
																		'div',
																		{ 'class': 'col-md-5' },
																		React.createElement('img', { src: 'page/img/theme-example.png', 'class': 'bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto', width: '500', height: '500', xmlns: 'http://www.w3.org/2000/svg', preserveAspectRatio: 'xMidYMid slice', focusable: 'false', role: 'img', 'aria-label': 'Placeholder: 500x500' })
															)
												),
												React.createElement('hr', { 'class': 'featurette-divider' }),
												React.createElement(
															'div',
															{ 'class': 'row featurette' },
															React.createElement(
																		'div',
																		{ 'class': 'col-md-7 order-md-2' },
																		React.createElement(
																					'h2',
																					{ 'class': 'featurette-heading' },
																					'\u0418\u043D\u0441\u0442\u0440\u0443\u043C\u0435\u043D\u0442\u044B ',
																					React.createElement('br', null),
																					React.createElement(
																								'span',
																								{ 'class': 'text-muted' },
																								'\u041F\u043E\u043B\u0435\u0437\u043D\u044B\u0435 \u043D\u0430\u0434\u0441\u0442\u0440\u043E\u0439\u043A\u0438'
																					)
																		),
																		React.createElement(
																					'p',
																					{ 'class': 'lead' },
																					'\u041F\u0440\u044F\u043C\u043E\u0439 \u0434\u043E\u0441\u0442\u0443\u043F \u043A \u043F\u0430\u043C\u044F\u0442\u0438 \u0438 \u0434\u0435\u0431\u0430\u0433\u0433\u0435\u0440 \u0432 \u0432\u0430\u0448\u0435\u043C \u0440\u0430\u0441\u043F\u043E\u0440\u044F\u0436\u0435\u043D\u0438\u0438'
																		)
															),
															React.createElement(
																		'div',
																		{ 'class': 'col-md-5 order-md-1' },
																		React.createElement('img', { src: 'page/img/tools-example.png', 'class': 'bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto', width: '500', height: '500', xmlns: 'http://www.w3.org/2000/svg', preserveAspectRatio: 'xMidYMid slice', focusable: 'false', role: 'img', 'aria-label': 'Placeholder: 500x500' })
															)
												),
												React.createElement('hr', { 'class': 'featurette-divider' })
									)
						);
			}
}