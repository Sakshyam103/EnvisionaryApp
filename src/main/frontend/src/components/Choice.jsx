// const Statistics = () => {
//     let data = "Statistics";
//     fetch("http://localhost:8080/example", {
//       method:"POST",
//       body: JSON.stringify(data),
//       headers:{
//         "Content-Type": "application/json",
//        },
//       }).then(res => {
//         if(!res.ok){
//           console.error('Request failed with status:' , res.status);
//           return res.text();
//         }
//         return res.text();
//       })
//       .then(data => {
//         console.log(data);
//       }).catch(error=>{
//         console.error('Error: ', error);
//       })
// }

// export default Statistics