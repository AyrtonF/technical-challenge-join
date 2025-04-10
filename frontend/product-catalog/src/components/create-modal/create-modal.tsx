import { useState } from "react"
import { useProductUpdateData } from "../../hooks/useProductUpdateData";
import { ProductData } from "../../interfaces/ProductData";
import "./modal.css"
interface InputProps{
    label: string,
    value: string,
    updateValue:(value: any) => void

}

const Input = ({label, value, updateValue}: InputProps) => {
    return(
        <div>
                <label htmlFor="">{label}</label>
                <input value={value} onChange={e => updateValue(e.target.value)} />
        </div>
    )
}




export function CreateModal(){
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [image, setImage] = useState("")
    const {mutate} = useProductUpdateData();

const submit = ()=>{
    const productData: ProductData ={
        name,
        description, image
    }

    mutate(productData);

}

return(
    <div className="modal-overlay">
        <div className="modal-body">
            <h2>Cadastre um novo produto</h2>
            <form className="input-container" action="">

                    <Input label="image" value={image} updateValue={setImage}></Input>
                    <Input label="name" value={name} updateValue={setName}></Input>
                    <Input label="description" value={description} updateValue={setDescription}></Input>
                
            </form>
            <button onClick={submit} className="btn-secondary">Cadastrar</button>
        </div>

    </div>
)

}