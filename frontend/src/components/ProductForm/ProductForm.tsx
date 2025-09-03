import { useState, useEffect, useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import ProductService from "../../services/productService";
import { Product } from "../../types/product";
import "./ProductForm.css";

const ProductForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [product, setProduct] = useState<Omit<Product, "id"> | Product>({
    name: "",
    description: "",
    price: 0,
    quantity: 0,
    imageUrl: "",
  });

  const [isLoading, setIsLoading] = useState(false);
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [isImageValid, setIsImageValid] = useState<boolean | null>(null);
  const [imageLoading, setImageLoading] = useState(false);
  const currentImageRef = useRef<HTMLImageElement | null>(null);
  const debounceRef = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    if (id) {
      const fetchProduct = async () => {
        setIsLoading(true);
        try {
          const data = await ProductService.getById(id);
          setProduct(data);
          // Validar imagem ao carregar produto
          if (data.imageUrl) {
            validateImage(data.imageUrl);
          }
        } catch {
          toast.error("Erro ao carregar o produto");
          navigate("/products");
        } finally {
          setIsLoading(false);
        }
      };
      fetchProduct();
    }

    // Cleanup ao desmontar componente
    return () => {
      if (currentImageRef.current) {
        currentImageRef.current.onload = null;
        currentImageRef.current.onerror = null;
        currentImageRef.current = null;
      }
      if (debounceRef.current) {
        clearTimeout(debounceRef.current);
      }
    };
  }, [id, navigate]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setProduct((prev) => ({ ...prev, [name]: value }));

    // Se for alteração na URL da imagem, validar com debounce
    if (name === "imageUrl") {
      // Se campo foi limpo, resetar imediatamente
      if (!value.trim()) {
        if (debounceRef.current) {
          clearTimeout(debounceRef.current);
        }
        setImagePreview(null);
        setIsImageValid(null);
        setImageLoading(false);
        return;
      }

      // Limpar timeout anterior
      if (debounceRef.current) {
        clearTimeout(debounceRef.current);
      }

      // Definir novo timeout para validação
      debounceRef.current = setTimeout(() => {
        validateImage(value);
      }, 300); // 300ms de debounce
    }
  };

  const validateImage = (url: string) => {
    // Cancelar validação anterior se existir
    if (currentImageRef.current) {
      currentImageRef.current.onload = null;
      currentImageRef.current.onerror = null;
      currentImageRef.current = null;
    }

    if (!url) {
      setImagePreview(null);
      setIsImageValid(null);
      setImageLoading(false);
      return;
    }

    setImageLoading(true);
    setIsImageValid(null);
    setImagePreview(null);

    // Testar se a imagem pode ser carregada (não usar regex, apenas teste real)
    const img = new Image();
    currentImageRef.current = img;

    img.onload = () => {
      // Verificar se esta ainda é a validação atual
      if (currentImageRef.current === img) {
        setIsImageValid(true);
        setImagePreview(url);
        setImageLoading(false);
        currentImageRef.current = null;
      }
    };

    img.onerror = () => {
      // Verificar se esta ainda é a validação atual
      if (currentImageRef.current === img) {
        setIsImageValid(false);
        setImagePreview(null);
        setImageLoading(false);
        currentImageRef.current = null;
      }
    };

    img.src = url;
  };

  const validate = () => {
    // Remover validação que impede salvamento
    // Apenas mostrar aviso visual, mas permitir salvar
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validate()) return;

    setIsLoading(true);

    try {
      if (id) {
        await ProductService.update(id, product as Product);
        toast.success("Produto atualizado com sucesso!");
      } else {
        await ProductService.create(product);
        toast.success("Produto criado com sucesso!");
      }
      navigate("/products");
    } catch {
      toast.error("Erro ao salvar o produto");
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading && id) {
    return <div className="loading">Carregando...</div>;
  }

  return (
    <div className="formCard">
      <div className="cardBody">
        <h2 className="cardTitle">
          {id ? "Editar Produto" : "Adicionar Novo Produto"}
        </h2>
        <form onSubmit={handleSubmit} className="form">
          <div className="formGroup">
            <label htmlFor="name" className="label">
              Nome do Produto <span className="required">*</span>
            </label>
            <input
              type="text"
              className="input"
              id="name"
              name="name"
              value={product.name}
              onChange={handleChange}
              required
              minLength={3}
            />
          </div>

          <div className="formGroup">
            <label htmlFor="description" className="label">
              Descrição
            </label>
            <textarea
              className="textarea"
              id="description"
              name="description"
              rows={3}
              value={product.description}
              onChange={handleChange}
            ></textarea>
          </div>

          <div className="row">
            <div className="col">
              <label htmlFor="price" className="label">
                Preço (R$) <span className="required">*</span>
              </label>
              <input
                type="number"
                step="0.01"
                min="0.01"
                className="input"
                id="price"
                name="price"
                value={product.price}
                onChange={handleChange}
                required
              />
            </div>

            <div className="col">
              <label htmlFor="quantity" className="label">
                Quantidade <span className="required">*</span>
              </label>
              <input
                type="number"
                min="0"
                step="1"
                className="input"
                id="quantity"
                name="quantity"
                value={product.quantity}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          <div className="formGroup">
            <label htmlFor="imageUrl" className="label">
              URL da Imagem
            </label>
            <input
              type="url"
              className="input"
              id="imageUrl"
              name="imageUrl"
              value={product.imageUrl || ""}
              onChange={handleChange}
              placeholder="https://exemplo.com/imagem.jpg"
            />

            {/* Preview da imagem */}
            {product.imageUrl && (
              <div className="imagePreviewContainer">
                {isImageValid === true && imagePreview ? (
                  <div className="imagePreview">
                    <img src={imagePreview} alt="Preview" />
                    <span className="previewLabel success">
                      ✓ Imagem válida
                    </span>
                  </div>
                ) : isImageValid === false ? (
                  <div className="imagePreview error">
                    <div className="noImagePlaceholder">❌</div>
                    <span className="previewLabel error">
                      Imagem inválida ou não encontrada
                    </span>
                  </div>
                ) : imageLoading ? (
                  <div className="imagePreview loading">
                    <div className="loadingSpinner">⏳</div>
                    <span className="previewLabel">Carregando...</span>
                  </div>
                ) : null}
              </div>
            )}
          </div>

          <div className="formActions">
            <button
              type="button"
              className="cancelButton"
              onClick={() => navigate("/products")}
            >
              Cancelar
            </button>
            <button type="submit" className="submitButton" disabled={isLoading}>
              {isLoading ? "Salvando..." : id ? "Atualizar" : "Salvar"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ProductForm;
