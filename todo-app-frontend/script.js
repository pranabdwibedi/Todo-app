let addForm = document.getElementById("addTaskForm")
addForm.addEventListener("submit", (event)=>{
    let title = document.getElementById("title");
    event.preventDefault();
    fetch("http://localhost:8080/task",{
        method:"POST",
        headers:{
            "Content-type":"application/json"
        },
        body:JSON.stringify({
            title: title.value
        })
    }).then(response=>{        
        console.log(response);
        location.reload()
        if(response.status == 400)
            alert("Task is already present");
    })
    .catch(error=>{
        console.log(error);
        
    })
})
document.addEventListener("DOMContentLoaded",()=>{
    let container = document.getElementById("taskContainer")
    let box = document.createElement("div");
    box.setAttribute("class", "d-flex justify-content-center align-items-center")
    fetch("http://localhost:8080/task", {
        method: "GET",
        headers:{
            "Content-type":"application/json"
        }
    }).then(async response=>{
        if(response.status == 404){
            let content = document.createElement("span")
            content.setAttribute("class", "boldText fs-3")
            content.innerText = "No Task Found"
            box.appendChild(content)
            container.appendChild(box)
            return
        }else{
            let data = await response.json()
            console.log(data);
            for(let task in data){
                box.setAttribute("class", "d-flex justify-content-between gap-3 align-items-center")
                let title = document.createElement("span")
                title.innerText = data[task].title
                title.setAttribute("class", "flex-grow-1")
                let toggle = document.createElement("i")
                toggle.setAttribute("onclick", `toggleTask(${data[task].id})`)

                if(data[task].completed == true){
                    toggle.setAttribute("class", "toggleIcon hoverPointer fa-regular fa-square-check fs-4")
                    title.setAttribute("class", "flex-grow-1 strikeText")
                    
                }else{
                    toggle.setAttribute("class", "toggleIcon hoverPointer fa-regular fa-square fs-4")
                }
                let deleteButton = document.createElement("button")
                deleteButton.setAttribute("onClick", `deleteTask(${data[task].id})`)
                deleteButton.setAttribute("type", "button")
                deleteButton.setAttribute("class", "btn btn-danger");
                deleteButton.innerText="Delete"
                box.appendChild(title)
                box.appendChild(toggle)
                box.appendChild(deleteButton)
                container.appendChild(box)
            }
        }
    })
})

let deleteTask = (id)=>{
    fetch(`http://localhost:8080/task/${id}`, {
        method:"DELETE",
        headers:{
            "Content-type" : "application/json"
        }
    }).then(response=>{
        if(response.status == 200){
            alert("Task deleted successfully")
            location.reload();
        }else{
            alert("Error : Couldn't delete the Task")
            location.reload()
        }
    }).catch(error=>{
        console.log(error);
    })
}

let toggleTask = (id) =>{
    fetch(`http://localhost:8080/task/toggle/${id}`, {
        method:"PUT",
        headers:{
            "Content-type" : "application/json"
        }
    }).then(response=>{
        if(response.status != 200)
            alert("Couldn't toggle the task")
        location.reload();
    }).catch(error=>{
        console.log(error);
    })
}