import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { FaArrowLeft, FaEdit } from "react-icons/fa";
import ProductService from "../../services/productService";
import { toast } from "react-toastify";
import { Product } from "../../types/product";
import "./ViewProduct.css";

const ViewProduct = () => {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<Product | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [imageError, setImageError] = useState(false);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        if (!id) {
          throw new Error("ID do produto não fornecido");
        }
        const data = await ProductService.getById(id);
        setProduct(data);
      } catch {
        toast.error("Erro ao carregar o produto");
      } finally {
        setIsLoading(false);
      }
    };
    fetchProduct();
  }, [id]);

  const handleImageError = () => {
    setImageError(true);
  };

  if (isLoading) {
    return <div className="loading">Carregando...</div>;
  }

  if (!product) {
    return <div className="error">Produto não encontrado</div>;
  }

  return (
    <div className="card">
      <div className="cardBody">
        <div className="header">
          <Link to="/products" className="backButton">
            <FaArrowLeft className="backIcon" /> Voltar para Produtos
          </Link>
          <Link to={`/products/edit/${product.id}`} className="editButton">
            <FaEdit className="editIcon" /> Editar
          </Link>
        </div>

        <div className="content">
          <div className="imageContainer">
            {product.imageUrl && !imageError ? (
              <img
                src={product.imageUrl}
                alt={product.name}
                className="image"
                onError={handleImageError}
              />
            ) : (
              <div className="noImage">Sem imagem</div>
            )}
          </div>
          <div className="details">
            <h2 className="productName">{product.name}</h2>
            {product.description && (
              <p className="description">{product.description}</p>
            )}

            <div className="productInfo">
              <div className="infoRow">
                <span className="infoLabel">Preço:</span>
                <span className="price">R$ {product.price.toFixed(2)}</span>
              </div>

              <div className="infoRow">
                <span className="infoLabel">Estoque:</span>
                <span className="quantity">{product.quantity} unidades</span>
              </div>

              {product.quantity === 0 && (
                <div className="outOfStockBadge">Produto Esgotado</div>
              )}

              {product.id && (
                <div className="infoRow">
                  <span className="infoLabel">ID:</span>
                  <span className="id">{product.id}</span>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewProduct;
