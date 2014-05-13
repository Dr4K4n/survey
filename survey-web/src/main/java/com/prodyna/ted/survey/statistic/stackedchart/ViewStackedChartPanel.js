$(function() {
	alert('hi');
	drawStackedChart();
});
function drawStackedChart() {
	$.plot('#allAnswersStackedChart', data, {
		series : {
			stack : 0,
			lines : {
				show : false,
				fill : true,
				steps : false
			},
			bars : {
				show : true,
				barWidth : 0.6
			}
		},
		legend : {
			show : true
		}
	});
}
window.onresize = function(event) {
	drawStackedChart();
};