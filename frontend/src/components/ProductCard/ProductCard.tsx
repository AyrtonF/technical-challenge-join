import { Link } from "react-router-dom";
import { FaEdit, FaTrash, FaEye } from "react-icons/fa";
import ProductService from "../../services/productService";
import { toast } from "react-toastify";
import { Product } from "../../types/product";
import "./ProductCard.css";

interface ProductCardProps {
  product: Product;
  onDelete: (id: string) => void;
}

const ProductCard = ({ product, onDelete }: ProductCardProps) => {
  const handleDelete = async () => {
    if (
      window.confirm("Tem certeza que deseja excluir este produto?") &&
      product.id
    ) {
      try {
        await ProductService.delete(product.id);
        toast.success("Produto excluído com sucesso!");
        onDelete(product.id);
      } catch (error) {
        toast.error("Erro ao excluir o produto");
      }
    }
  };

  return (
    <div className="cardContainer">
      <div className="card">
        <div className="cardImageContainer">
          {product.imageUrl ? (
            <img
              src={product.imageUrl}
              alt={product.name}
              className="cardImage"
            />
          ) : (
            <div className="noImage">Sem imagem</div>
          )}
        </div>
        <div className="cardBody">
          <h5 className="cardTitle">{product.name}</h5>
          <p className="cardText">{product.description}</p>
          <div className="cardFooter">
            <span className="priceBadge">R$ {product.price.toFixed(2)}</span>
            <div className="actions">
              <Link
                to={`/products/view/${product.id}`}
                className="actionButton"
              >
                <FaEye />
              </Link>
              <Link
                to={`/products/edit/${product.id}`}
                className="editButton"
              >
                <FaEdit />
              </Link>
              <button
                onClick={handleDelete}
                className="actionButton deleteButton"
              >
                <FaTrash />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
