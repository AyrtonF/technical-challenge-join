import { useEffect, useState } from "react"
import { useProductUpdateData } from "../../hooks/useProductUpdateData";
import { ProductData } from "../../interfaces/ProductData";
import "./modal.css"
interface InputProps{
    label: string,
    value: string,
    updateValue:(value: any) => void

}

interface ModalProps{
    closeModal: () => void
}

const Input = ({label, value, updateValue}: InputProps) => {
    return(
        <div>
                <label htmlFor="">{label}</label>
                <input value={value} onChange={e => updateValue(e.target.value)} />
        </div>
    )
}




export function CreateModal({closeModal}: ModalProps){
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [image, setImage] = useState("")
    const {mutate, isSuccess} = useProductUpdateData();

const submit = ()=>{
    const productData: ProductData ={
        name,
        description, image
    }

    mutate(productData);

}

useEffect(() => {
    if (!isSuccess) return;
    closeModal();
}, [isSuccess]);

return(
    <div className="modal-overlay">
        <div className="modal-body">
            <h2>Cadastre um novo produto</h2>
            <form className="input-container" action="">

                    <Input label="image url" value={image} updateValue={setImage}></Input>
                    <Input label="product name" value={name} updateValue={setName}></Input>
                    <Input label="description" value={description} updateValue={setDescription}></Input>
                
            </form>
            <button onClick={submit} className="btn-secondary">Cadastrar</button>
        </div>

    </div>
)

}