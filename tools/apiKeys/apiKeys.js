const getBtn = document.getElementById("get-btn");

const getData = () => {

    // Get the form value input
    var formJsonLinkVal = document.getElementById("formJsonLink").value;
    document.getElementById("formJsonLink").value="";
    document.getElementById("apiResult").innerHTML = "";

    axios.get(formJsonLinkVal, {
        // params: {
        //   'data.aPhaCode.phaCode': 'VA789'
        // },
        // headers: {
        //     "x-token": "vgTDUNFbaYGEVwct1CL9ckvzEvYfGf",
        //     "content-type": "application/json"
        // }
      })
      .then(function (response) {
        console.log(response);

        var outputText = "";
        outputText = outputText.concat("<h2>" + response.data.title + "</h2>");

        const pages = Object.entries(response.data.components);
        for (const [pagenum, pageData] of pages){
            if ('title' in pageData){
              outputText = outputText.concat("<p>" + pageData.title + "</p>");
              outputText = outputText.concat("<ul>");
              outputText = outputText.concat("<li>" + pageData.key + "</li>");
              const panelComponents = Object.entries(pageData.components);
              for (const [compNum, componentData]of panelComponents){
                  outputText = outputText.concat("<li>" + componentData.key + "</li>");
              }
              outputText = outputText.concat("</ul>");
          }
        }
        document.getElementById("apiResult").innerHTML = outputText;
      })
      .catch(function (error) {
        console.log(error);
        document.getElementById("apiResult").innerHTML = error;
      })
      .then(function () {
        // always executed
      });
};

getBtn.addEventListener("click", getData);