package bookstore.javabrightbrains.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequestDto {

    @NotNull(message = "Cart ID is required")
    private Long cartId;

    @NotBlank(message = "Delivery address is required")
    @Size(max = 255, message = "Delivery address cannot exceed 255 characters")
    private String deliveryAddress;

    @NotBlank(message = "Contact phone is required")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Invalid phone number format")
    private String contactPhone;

    @NotBlank(message = "Delivery method is required")
    @Pattern(regexp = "Standard|Express", message = "Invalid delivery method. Allowed values are: Standard, Express")
    private String deliveryMethod;

}