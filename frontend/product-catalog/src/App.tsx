import { useState } from 'react'
import './App.css'
import { Card } from './components/card/card'
import { useProductData } from './hooks/useProductData'
import { ProductData } from './interfaces/ProductData'
import { CreateModal } from './components/create-modal/create-modal'

function App() {
 
const {data} = useProductData()
const [isModalOpen, setIsModalOpen] = useState(false);

const handleOpenModal = () =>{
  setIsModalOpen(previous => !previous)
}

  return (
   <div className='container-app'>
    <h1>Catalogo de Produtos</h1>
    <div className='card-div'>
      {data?.map(productData => <Card 
      image={productData.image}
      name={productData.name} 
      description={productData.description}
      />)}
      </div>
      {isModalOpen && <CreateModal/>}
      <button onClick={handleOpenModal}>Cadastrar produto</button>
   </div>
  )
}

export default App
