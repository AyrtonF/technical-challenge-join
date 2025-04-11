import { Link } from 'react-router-dom'
import { FaBoxOpen, FaPlusCircle } from 'react-icons/fa'
import "./Home.css"

const Home = () => {
  return (
    <div className="homeContainer">
      <div className="hero">
        <h1 className="title">Bem-vindo ao Gerenciador de Produtos</h1>
        <p className="subtitle">
          Uma aplicação CRUD simples para gerenciar produtos com React e Spring Boot
        </p>
      </div>
      <div className="features">
        <div className="featureCard">
          <div className="featureIcon">
            <FaBoxOpen />
          </div>
          <h3 className="featureTitle">Visualizar Produtos</h3>
          <p className="featureText">Veja todos os produtos disponíveis no sistema</p>
          <Link to="/products" className="featureButton">
            Ver Produtos
          </Link>
        </div>
        <div className="featureCard">
          <div className="featureIcon">
            <FaPlusCircle />
          </div>
          <h3 className="featureTitle">Adicionar Produto</h3>
          <p className="featureText">Adicione um novo produto ao inventário</p>
          <Link to="/products/add" className="featureButton">
            Adicionar Produto
          </Link>
        </div>
      </div>
    </div>
  )
}

export default Home
