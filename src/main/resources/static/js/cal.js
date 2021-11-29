/*<![CDATA[*/
var scheduleYes=/*[[${schedules}]]*/;
//scheduleYes = /*[[${schedules}]]*/; ///여기다가 스케줄 밑에 형식으로 넣으면 됩니다
//scheduleYes.push("12/13/2021");

var today = new Date();
var dates = new Array(35);
var months = new Array(35);
var years = new Array(35);
var plus = 0;
var startDay = today.getDay();
for (var i = startDay; i < 35; i++) {
    var TODAY = new Date();
    var temp = new Date(TODAY.setDate(TODAY.getDate() + plus));
    plus++;
    dates[i] = temp.getDate();
    months[i] = temp.getMonth() + 1;
    years[i] = temp.getFullYear();
}

var tableAddr = new Array(6);
var table = document.createElement("table");
table.setAttribute("id", "table");
//table.setAttribute("border",1);
var dateCount = 0;
var monthCount = 0;
var totalDateCount = 0;
for (var i = 0; i < 6; i++) {
    tableAddr[i] = new Array(8);
    tableAddr[i] = document.createElement("tr");
    for (var j = 0; j < 8; j++) {
        if (i == 0) {//맨위 요일 적는곳
            tableAddr[i][j] = document.createElement("th");
            tableAddr[i][j].style.fontWeight = "normal";
            var pp = document.createElement("p");
            var dayText = "";
            switch (j) {
                case 1: dayText = "일"; break;
                case 2: dayText = "월"; break;
                case 3: dayText = "화"; break;
                case 4: dayText = "수"; break;
                case 5: dayText = "목"; break;
                case 6: dayText = "금"; break;
                case 7: dayText = "토"; break;
            }
            var dayTextNode = document.createTextNode(dayText);
            tableAddr[i][j].appendChild(dayTextNode);
        } else {
            if (j == 0) { //왼쪽 몇월인지 적을곳
                tableAddr[i][j] = document.createElement("th");
                tableAddr[i][j].style.fontWeight = "normal";
                var divv = document.createElement("div");
                var monthTextNode = document.createTextNode(months[monthCount + 6] + "월");
                divv.appendChild(monthTextNode);
                tableAddr[i][j].appendChild(divv);
                monthCount += 7;
            } else {
                tableAddr[i][j] = document.createElement("td");
                //tableAddr[i][j].style.backgroundColor="red";
                tableAddr[i][j].setAttribute("width", 40);
                tableAddr[i][j].setAttribute("height", 50);
                var divv = document.createElement("div");
                divv.setAttribute("id", months[totalDateCount] + "/" + dates[totalDateCount] + "/" + years[totalDateCount]);
                totalDateCount++;
                divv.style.borderRadius = "5px";
                divv.style.backgroundColor = "#d5d5d5";
                divv.style.textAlign = "center";

                divv.setAttribute("width", 40);
                divv.setAttribute("height", 50);
                var dateText = "";
                if (dates[dateCount] != undefined) {
                    dateText = dates[dateCount];
                    var dateTextNode = document.createTextNode(dateText);
                    divv.appendChild(dateTextNode);
                    divv.style.padding = "5px";
                    divv.style.paddingTop = "10px";
                    divv.style.paddingBottom = "10px";
                }
                dateCount++;
                tableAddr[i][j].appendChild(divv);

            }
        }
        tableAddr[i].appendChild(tableAddr[i][j]);
    }
    table.appendChild(tableAddr[i]);
}
document.getElementById("setTable").appendChild(table);

for (var i = 0; i < scheduleYes.length; i++) {
    document.getElementById(scheduleYes[i]).style.backgroundColor = "#A0CD63"; //색깔 테스트용
}

/*]]>*/