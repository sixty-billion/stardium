function getAddress() {
    new daum.Postcode({
        oncomplete: function (data) {
            let fullAddress = data.address;
            let address = fullAddress.split(' ');

            if (address[0] !== "서울") {
                alert("서울시만 가능합니다.");
                return;
            }

            document.querySelector("#fullAddress").value = fullAddress;
            document.querySelector("#fullAddress").disabled = true;
            document.querySelector("#city").value = address[0];
            document.querySelector("#section").value = address[1];

            let detailAddress = '';

            for (let i = 2; i < address.length; i++) {
                detailAddress += address[i] + " ";
            }

            document.querySelector("#detail").value = detailAddress;
        }
    }).open();
}